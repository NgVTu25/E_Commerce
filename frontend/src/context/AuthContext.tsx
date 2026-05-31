import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
  type ReactNode,
} from 'react';
import { login as apiLogin, register as apiRegister } from '../api/auth';
import type { AuthResponse, LoginRequest, RegisterRequest } from '../types';

type AuthContextValue = {
  isAuthenticated: boolean;
  username: string;
  roles: string[];
  login: (data: LoginRequest) => Promise<void>;
  register: (data: RegisterRequest) => Promise<void>;
  logout: () => void;
};

const AuthContext = createContext<AuthContextValue | null>(null);

function persistAuth(data: AuthResponse) {
  localStorage.setItem('accessToken', data.accessToken);
  localStorage.setItem('refreshToken', data.refreshToken);
  localStorage.setItem('username', data.username);
  localStorage.setItem('roles', JSON.stringify(data.roles));
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const [username, setUsername] = useState('');
  const [roles, setRoles] = useState<string[]>([]);
  const [ready, setReady] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    const storedUser = localStorage.getItem('username');
    const storedRoles = localStorage.getItem('roles');
    if (token && storedUser) {
      setUsername(storedUser);
      setRoles(storedRoles ? JSON.parse(storedRoles) : []);
    }
    setReady(true);
  }, []);

  const login = useCallback(async (data: LoginRequest) => {
    const res = await apiLogin(data);
    persistAuth(res);
    setUsername(res.username);
    setRoles(res.roles);
  }, []);

  const register = useCallback(async (data: RegisterRequest) => {
    const res = await apiRegister(data);
    persistAuth(res);
    setUsername(res.username);
    setRoles(res.roles);
  }, []);

  const logout = useCallback(() => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('username');
    localStorage.removeItem('roles');
    setUsername('');
    setRoles([]);
  }, []);

  const value = useMemo(
    () => ({
      isAuthenticated: Boolean(username) && Boolean(localStorage.getItem('accessToken')),
      username,
      roles,
      login,
      register,
      logout,
    }),
    [username, roles, login, register, logout],
  );

  if (!ready) {
    return null;
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return ctx;
}
