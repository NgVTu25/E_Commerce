export interface Product {
  productId?: number;
  productName: string;
  quantityPerUnit?: string;
  unitPrice: number;
  unitsInStock: number;
  unitsOnOrder?: number;
  reorderLevel?: number;
  discontinued?: number;
  categoryId: number;
  supplierId: number;
}

export interface Category {
  categoryId?: number;
  categoryName: string;
  description?: string;
}

export interface Customer {
  customerId?: string;
  companyName: string;
  contactTitle?: string;
  address: string;
  city: string;
  region?: string;
  postalCode?: string;
  phone: string;
  fax?: string;
}

export interface Supplier {
  supplierId?: number;
  companyName: string;
  contactName: string;
  contactTitle?: string;
  address: string;
  city: string;
  region?: string;
  postalCode?: string;
  country: string;
  phone: string;
  fax?: string;
  homePage?: string;
}

export interface Shipper {
  shipperId?: number;
  companyName: string;
  phone: string;
}

export interface Employee {
  employeeId: number;
  lastName: string;
  firstName: string;
  title?: string;
  titleOfCourtesy?: string;
  city?: string;
  country?: string;
  homePhone?: string;
  reportsTo?: number;
  territoryIds?: string[];
}

export interface TerritoryEmployee {
  employeeId: number;
  fullName: string;
  title?: string;
  city?: string;
}

export interface Territory {
  territoryId: string;
  territoryDescription: string;
  regionId?: number;
  regionDescription?: string;
  employees?: TerritoryEmployee[];
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  fullName?: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  username: string;
  roles: string[];
}
