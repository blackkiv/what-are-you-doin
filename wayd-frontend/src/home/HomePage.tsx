import {useQuery} from '@tanstack/react-query'
import {appStats} from '../api/logs'
import {PieChart} from '@mui/x-charts'

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

const HomePage = () => {
  const $stats = useQuery({queryKey: ['appStats'], queryFn: appStats})

  return (
    !$stats.isLoading && (
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
              highlightScope: {fade: 'global', highlight: 'item'},
              faded: {innerRadius: 30, additionalRadius: -30, color: 'gray'},
              valueFormatter: (item: { value: number }) =>
                formatSeconds(item.value),
            },
          ]}
          width={1000}
          height={400}
        />
      </>
    )
  )
}

export default HomePage
