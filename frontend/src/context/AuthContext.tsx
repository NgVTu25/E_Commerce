import {
  createContext,
  useCallback,
  useContext,
  useMemo,
  useState,
  type ReactNode,
} from 'react';
import { login as apiLogin, register as apiRegister } from '../api/auth';
import type { AuthResponse, LoginRequest, RegisterRequest } from '../types';

interface AuthState {
  username: string | null;
  roles: string[];
  isAuthenticated: boolean;
}

interface AuthContextValue extends AuthState {
  login: (data: LoginRequest) => Promise<void>;
  register: (data: RegisterRequest) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextValue | null>(null);

function readStoredAuth(): AuthState {
  const username = localStorage.getItem('username');
  const rolesRaw = localStorage.getItem('roles');
  const roles = rolesRaw ? (JSON.parse(rolesRaw) as string[]) : [];
  return {
    username,
    roles,
    isAuthenticated: Boolean(localStorage.getItem('accessToken')),
  };
}

function persistAuth(response: AuthResponse): void {
  localStorage.setItem('accessToken', response.accessToken);
  localStorage.setItem('refreshToken', response.refreshToken);
  localStorage.setItem('username', response.username);
  localStorage.setItem('roles', JSON.stringify(response.roles ?? []));
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const [auth, setAuth] = useState<AuthState>(readStoredAuth);

  const applyAuth = useCallback((response: AuthResponse) => {
    persistAuth(response);
    setAuth({
      username: response.username,
      roles: response.roles ?? [],
      isAuthenticated: true,
    });
  }, []);

  const login = useCallback(
    async (data: LoginRequest) => {
      const response = await apiLogin(data);
      applyAuth(response);
    },
    [applyAuth],
  );

  const register = useCallback(
    async (data: RegisterRequest) => {
      const response = await apiRegister(data);
      applyAuth(response);
    },
    [applyAuth],
  );

  const logout = useCallback(() => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('username');
    localStorage.removeItem('roles');
    setAuth({ username: null, roles: [], isAuthenticated: false });
  }, []);

  const value = useMemo(
    () => ({ ...auth, login, register, logout }),
    [auth, login, register, logout],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth(): AuthContextValue {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return ctx;
}
