import { useQuery } from '@tanstack/react-query'
import {
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from 'recharts'
import { format } from 'date-fns'
import { useMemo } from 'react'
import { appsUsageBreakdown, AppsUsageBreakdownResponse } from '../../../api'
import { formatSeconds } from '../../../util'

export const chatColors = [
  '#10002B',
  '#240046',
  '#3C096C',
  '#5A189A',
  '#7B2CBF',
  '#9D4EDD',
  '#C77DFF',
  '#E0AAFF',
  '#150033',
  '#2A0050',
  '#45087A',
  '#6622A3',
  '#8A37C8',
  '#B058EB',
  '#D892FF',
  '#F0C7FF',
]

const formatDate = (date: Date) => {
  return format(date, 'dd MMM')
}

const convertChartData = (data: AppsUsageBreakdownResponse) => {
  const groupedData: Record<string, Record<string, number | null>> = {}

  data.forEach(({ appName, usageDate, usageSeconds }) => {
    if (!groupedData[appName]) {
      groupedData[appName] = {}
    }
    groupedData[appName][formatDate(usageDate)] = usageSeconds
  })

  const uniqueDates = Array.from(
    new Set(data.map(item => formatDate(item.usageDate))),
  ).sort()

  const chartData = uniqueDates.map(date => {
    const entry: Record<string, any> = { date }
    Object.keys(groupedData).forEach(appName => {
      entry[appName] = groupedData[appName][date] ?? null
    })
    return entry
  })

  const maxUsage = Math.max(...data.map(entry => entry.usageSeconds))
  const minUsage = Math.min(...data.map(entry => entry.usageSeconds))

  return { chartData, appNames: Object.keys(groupedData), maxUsage, minUsage }
}

export const LineChartStats = () => {
  const $appsUsageBreakdown = useQuery({
    queryKey: ['appsUsageBreakdown'],
    queryFn: appsUsageBreakdown,
  })

  const chartData = useMemo(() => {
    if ($appsUsageBreakdown.isSuccess) {
      return convertChartData($appsUsageBreakdown.data!)
    }
  }, [$appsUsageBreakdown.data, $appsUsageBreakdown.isSuccess])

  return (
    chartData && (
      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={chartData.chartData}>
          <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
          <XAxis dataKey="date" />
          <YAxis
            tickCount={
              chartData.maxUsage / chartData.minUsage > 8
                ? 8
                : chartData.maxUsage / chartData.minUsage
            }
            width={100}
            tickFormatter={value => formatSeconds(value) ?? ''}
          />
          <Tooltip formatter={value => formatSeconds(value as number)} />
          <Legend />
          {chartData.appNames.map((appName, index) => {
            return (
              <Line
                key={appName}
                type="monotone"
                dataKey={appName}
                name={appName}
                stroke={chatColors[index % chatColors.length]}
                dot={{ r: 4 }}
                connectNulls={false}
              />
            )
          })}
        </LineChart>
      </ResponsiveContainer>
    )
  )
}
