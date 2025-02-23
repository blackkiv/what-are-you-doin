import axios from 'axios'

export type LoginPayload = {
  username: string
  rawPassword: string
}

export type LoginResponse = string

export const login = async (payload: LoginPayload) => {
  const response = await axios.post<LoginResponse>(
    'http://localhost:8765/auth',
    payload,
  )
  return response.data
}
