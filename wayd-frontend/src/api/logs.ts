import axios from 'axios'

export type AppStatsResponse = {
  appName: string
  elapsedTime: number
}[]

export const appStats = async () => {
  const response = await axios.get<AppStatsResponse>(
    'http://localhost:8765/logs/stats',
    {
      headers: {
        'User-Token': localStorage.getItem('Wayd-Token'),
      },
    },
  )
  return response.data
}
