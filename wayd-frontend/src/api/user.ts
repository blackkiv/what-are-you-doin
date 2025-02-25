import axios from 'axios'
import { backendUrl } from './backendUtil.ts'

export type UserDataResponse = {
  username: string
  preference: {
    apps: string[]
    appsBlacklist: string[]
    appsWhitelist: string[]
  }
}

export const userData = async () => {
  const response = await axios.get<UserDataResponse>(`${backendUrl()}/users`, {
    headers: {
      'User-Token': localStorage.getItem('Wayd-Token'),
    },
  })
  return response.data
}

export type UpdateUserPreference = {
  appsBlacklist: string[]
  appsWhitelist: string[]
}

export const updateUserPreference = async (payload: UpdateUserPreference) => {
  await axios.put(`${backendUrl()}/users/preference`, payload, {
    headers: {
      'User-Token': localStorage.getItem('Wayd-Token'),
    },
  })
}
