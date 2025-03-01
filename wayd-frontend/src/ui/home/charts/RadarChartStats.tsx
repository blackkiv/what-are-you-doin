import { useQuery } from '@tanstack/react-query'
import { appStats } from '../../../api'
import {
  PolarAngleAxis,
  PolarGrid,
  PolarRadiusAxis,
  Radar,
  RadarChart,
  ResponsiveContainer,
  Tooltip,
} from 'recharts'
import { useMemo } from 'react'

export const RadarChartStats = () => {
  const $stats = useQuery({ queryKey: ['appStats'], queryFn: appStats })

  const chartData = useMemo(() => {
    if ($stats.isSuccess) {
      return $stats.data
    }
  }, [$stats.data, $stats.isSuccess])

  return (
    chartData && (
      <ResponsiveContainer width="100%" height={400}>
        <RadarChart cx="50%" cy="50%" outerRadius="80%" data={chartData}>
          <PolarGrid />
          <PolarAngleAxis dataKey="appName" />
          <PolarRadiusAxis />
          <Radar
            name="usage time"
            dataKey="elapsedTime"
            stroke="#9D4EDD"
            fill="#9D4EDD"
            fillOpacity={0.6}
          />
          <Tooltip />
        </RadarChart>
      </ResponsiveContainer>
    )
  )
}
