import { useQuery } from '@tanstack/react-query'
import { appStats } from '../api/logs'
import { PieChart } from '@mui/x-charts'
import { useEffect } from 'react'
import { AxiosError } from 'axios'
import { useNavigate } from 'react-router-dom'
import { Typography } from '@mui/material'

const formatSeconds = (seconds: number) => {
  const hours = Math.floor(seconds / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  const remainingSeconds = seconds % 60

  let formattedTime = ''

  if (hours > 0) {
    formattedTime += `${hours}h `
  }
  if (minutes > 0 || hours > 0) {
    formattedTime += `${minutes}m `
  }
  formattedTime += `${remainingSeconds}s`

  return formattedTime.trim()
}

const DashboardPage = () => {
  const navigate = useNavigate()

  const $stats = useQuery({ queryKey: ['appStats'], queryFn: appStats })

  useEffect(() => {
    if ($stats.error instanceof AxiosError) {
      const error = $stats.error
      if (error.status === 400) {
        localStorage.removeItem('Wayd-Token')
        navigate('/login')
      }
    }
  }, [$stats.error])

  return $stats.isLoading || !$stats.data ? (
    <Typography>No data :c</Typography>
  ) : (
    <>
      <PieChart
        series={[
          {
            data:
              $stats.data?.map((appUsage, index) => ({
                id: index,
                value: appUsage.elapsedTime,
                label: appUsage.appName,
              })) ?? [],
            highlightScope: { fade: 'global', highlight: 'item' },
            faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' },
            valueFormatter: (item: { value: number }) =>
              formatSeconds(item.value),
          },
        ]}
        width={1000}
        height={400}
      />
    </>
  )
}

export default DashboardPage
