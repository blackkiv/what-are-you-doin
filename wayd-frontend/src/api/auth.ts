import axios from 'axios'
import { backendUrl } from './backendUtil'

export type LoginPayload = {
  username: string
  rawPassword: string
}

export type LoginResponse = string

export const login = async (payload: LoginPayload) => {
  const response = await axios.post<LoginResponse>(
    `${backendUrl()}/auth`,
    payload,
  )
  return response.data
}

export type RegisterPayload = {
  username: string
  rawPassword: string
}

export type RegisterResponse = string

export const register = async (payload: RegisterPayload) => {
  const response = await axios.post<RegisterResponse>(
    `${backendUrl()}/users`,
    payload,
  )
  return response.data
}
