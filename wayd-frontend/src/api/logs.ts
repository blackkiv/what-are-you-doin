import axios from 'axios'
import { backendUrl } from './backendUtil'

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

export type AppsUsageBreakdownResponse = {
  appName: string
  usageDate: Date
  usageSeconds: number
}[]

export const appsUsageBreakdown = async () => {
  const response = await axios.get<AppsUsageBreakdownResponse>(
    `${backendUrl()}/logs/stats/usage-breakdown`,
    {
      headers: {
        'User-Token': localStorage.getItem('Wayd-Token'),
      },
    },
  )
  return response.data
}
