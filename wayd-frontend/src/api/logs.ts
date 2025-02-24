import axios from 'axios'
import { backendUrl } from './backendUtil.ts'

export type AppStatsResponse = {
  appName: string
  elapsedTime: number
}[]

export const appStats = async () => {
  const response = await axios.get<AppStatsResponse>(
    `${backendUrl()}/logs/stats`,
    {
      headers: {
        'User-Token': localStorage.getItem('Wayd-Token'),
      },
    },
  )
  return response.data
}
