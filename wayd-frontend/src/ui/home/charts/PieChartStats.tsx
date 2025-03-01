import { useQuery } from '@tanstack/react-query'
import { appStats } from '../../../api'
import { useMemo } from 'react'
import { Cell, Pie, PieChart, ResponsiveContainer, Tooltip } from 'recharts'
import { chatColors } from './LineChartStats.tsx'
import { formatSeconds } from '../../../util'

export const PieChartStats = () => {
  const $stats = useQuery({ queryKey: ['appStats'], queryFn: appStats })

  const chartData = useMemo(() => {
    if ($stats.isSuccess) {
      return $stats.data
    }
  }, [$stats.data, $stats.isSuccess])

  return (
    chartData && (
      <ResponsiveContainer width="100%" height={600}>
        <PieChart>
          <Pie
            data={chartData}
            dataKey="elapsedTime"
            cx="50%"
            cy="50%"
            label={data => data.payload.name}
          >
            {chartData.map(({ appName }, index) => (
              <Cell
                name={appName}
                key={appName}
                fill={chatColors[index % chatColors.length]}
              />
            ))}
          </Pie>
          <Tooltip formatter={value => formatSeconds(value as number)} />
        </PieChart>
      </ResponsiveContainer>
    )
  )
}
