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
import CardWidgetsInflightOrders from "../../views/ui/widgets/CardWidgetsInflightOrders";
import CardWidgetsWeeklyOverview from "../../views/ui/widgets/CardWidgetsWeeklyOverview";
import CardWidgetsSalesProfit from "../../views/ui/widgets/CardWidgetsSalesProfit";
import CardWidgetsOrdersByDay from "../../views/ui/widgets/CardWidgetsOrdersByDay";
import CardWidgetsSvcFulfillment from "../../views/ui/widgets/CardWidgetsSvcFulfillment";

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
      <Grid item xs={12} sm={6} md={4}>
        <CardWidgetsOrdersByDay />
      </Grid>
      <Grid item xs={12} sm={6} md={4}>
        <CardWidgetsSvcFulfillment />
      </Grid>
      <Grid item xs={12} sm={6} md={4}>
        <CardWidgetsSalesProfit />
      </Grid>
      <Grid item xs={12}>
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
      </Grid>
      <Grid item xs={12} md={8}>
        <CardWidgetsInflightOrders />
      </Grid>
      <Grid item xs={12} md={4}>
        <CardWidgetsWeeklyOverview />
      </Grid>
    </Grid>
  )
}

export default Home
