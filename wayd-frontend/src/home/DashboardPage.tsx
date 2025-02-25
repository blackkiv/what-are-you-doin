import { Stack } from '@mui/material'
import PieChartStats from './charts/PieChartStats.tsx'
import LineChartStats from './charts/LineChartStats.tsx'

const DashboardPage = () => {
  return (
    <>
      <Stack spacing={2} alignItems="center">
        <PieChartStats />
        <LineChartStats />
      </Stack>
    </>
  )
}

export default DashboardPage
