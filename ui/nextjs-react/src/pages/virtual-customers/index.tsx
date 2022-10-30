// ** React Imports
import { useContext } from 'react'

// ** Context Imports
import { AbilityContext } from 'src/layouts/components/acl/Can'

// ** MUI Imports
import Grid from '@mui/material/Grid'
import Card from '@mui/material/Card'
import CardHeader from '@mui/material/CardHeader'
import Typography from '@mui/material/Typography'
import CardContent from '@mui/material/CardContent'

const VirtualCustomers = () => {
  // ** Hooks
  const ability = useContext(AbilityContext)

  return (
    <Grid container spacing={6}>
      <Grid item md={6} xs={12}>
        <Card>
          <CardHeader title='Trigger orders' />
          <CardContent>
            <Typography sx={{ mb: 4 }}>TODO: Add virtual customers form</Typography>
            <Typography sx={{ color: 'primary.main' }}>TBD</Typography>
          </CardContent>
        </Card>
      </Grid>
      {ability?.can('read', 'analytics') ? (
        <Grid item md={6} xs={12}>
          <Card>
            <CardHeader title='Current Orders' />
            <CardContent>
              <Typography sx={{ mb: 4 }}>TODO: Current Orders</Typography>
              <Typography sx={{ color: 'error.main' }}>TBD</Typography>
            </CardContent>
          </Card>
        </Grid>
      ) : null}
    </Grid>
  )
}

VirtualCustomers.acl = {
  action: 'read',
  subject: 'acl-page'
}

export default VirtualCustomers
