export function hasManagerAccess(roles: string[]): boolean {
  return roles.some((r) => r === 'ROLE_ADMIN' || r === 'ROLE_MANAGER');
}

export function isAdmin(roles: string[]): boolean {
  return roles.includes('ROLE_ADMIN');
}
