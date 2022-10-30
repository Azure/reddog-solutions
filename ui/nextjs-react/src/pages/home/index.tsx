// ** MUI Imports
import Card from '@mui/material/Card'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import CardHeader from '@mui/material/CardHeader'
import CardContent from '@mui/material/CardContent'
import { useEffect, useState } from "react";

// ** MUI Imports
import Link from '@mui/material/Link'

// ** Custom Components Imports
import PageHeader from 'src/@core/components/page-header'

// ** Hooks
import { useSettings } from 'src/@core/hooks/useSettings'

// ** Styled Components
import RechartsWrapper from 'src/@core/styles/libs/recharts'
import DatePickerWrapper from 'src/@core/styles/libs/react-datepicker'

// ** Demo Components Imports
import RechartsLineChart from 'src/views/charts/recharts/RechartsLineChart'
import RechartsAreaChart from 'src/views/charts/recharts/RechartsAreaChart'

// ** Third Party Styles Imports
import 'react-datepicker/dist/react-datepicker.css'

const Home = () => {

  const [stats, setStats] = useState(null)

  const MINUTE_MS = 500000

  const { settings } = useSettings()

  useEffect(() => {
    const interval = setInterval(() => {
      fetch('https://63594e33ff3d7bddb99ee138.mockapi.io/stats/1')
        .then((res) => res.json())
        .then((data) => {
          console.log(data)
          setStats(data.stats)
        })
    }, MINUTE_MS);

    return () => clearInterval(interval); // This represents the unmount function, in which you need to clear your interval to prevent memory leaks.
  }, [stats])

  return (
    <Grid container spacing={6}>
      <Grid item xs={12}>
        <Card>
          <CardHeader title='Orders over time 🚀'></CardHeader>
          <CardContent>
            <RechartsWrapper>
              <DatePickerWrapper>
                <Grid container spacing={6}>
                  <Grid item xs={12} md={6}>
                  <RechartsLineChart direction={settings.direction} />
                  </Grid>
                  <Grid item xs={12} md={6}>
                  <RechartsAreaChart direction={settings.direction} />
                  </Grid>
                </Grid>
              </DatePickerWrapper>
            </RechartsWrapper>
          </CardContent>
        </Card>
      </Grid>
      <Grid item xs={12}>
        <Card>
          <CardHeader title='Order Activity'></CardHeader>
          <CardContent>
            <Typography sx={{ mb: 2 }}>
              TODO: Add order timeline.
            </Typography>
          </CardContent>
        </Card>
      </Grid>
    </Grid>
  )
}

export default Home
