import { Stack, Typography } from '@mui/material'
import { LineChartStats, PieChartStats } from './charts'
import { useContext } from 'react'
import { UserContext } from '../../util'

export const DashboardPage = () => {
  const user = useContext(UserContext)

  return (
    <>
      <Stack spacing={2} alignItems="center">
        <Typography width="100vh">Hello, {user.user.username}</Typography>
        <PieChartStats />
        <LineChartStats />
      </Stack>
    </>
  )
}
