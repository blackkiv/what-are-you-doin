import { LineChart } from '@mui/x-charts'
import { useQuery } from '@tanstack/react-query'
import { appsUsageBreakdown } from '../../api/logs.ts'
import { format } from 'date-fns'

const formatDate = (date: Date) => {
  return format(date, 'dd MMM')
}

const LineChartStats = () => {
  const $appsUsageBreakdown = useQuery({
    queryKey: ['appsUsageBreakdown'],
    queryFn: appsUsageBreakdown,
  })

  return (
    !$appsUsageBreakdown.isLoading && (
      <>
        <LineChart
          xAxis={[
            {
              scaleType: 'band',
              data: $appsUsageBreakdown.data?.xAxis.map(formatDate),
            },
          ]}
          series={
            $appsUsageBreakdown.data?.appsUsageBreakdown.map(appUsage => ({
              data: appUsage.usageBreakdown,
            })) ?? []
          }
          width={1000}
          height={400}
        />
      </>
    )
  )
}

export default LineChartStats
