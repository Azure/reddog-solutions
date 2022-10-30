// ** Icon imports
import ViewDashboardOutline from 'mdi-material-ui/ViewDashboardOutline';
import MapMarkerRadiusOutline from 'mdi-material-ui/MapMarkerRadiusOutline'
import AccountGroupOutline from 'mdi-material-ui/AccountGroupOutline'
import { WrenchOutline } from 'mdi-material-ui';

// ** Type import
import { HorizontalNavItemsType } from 'src/@core/layouts/types'

const navigation = (): HorizontalNavItemsType => [
  {
    title: 'Dasboard',
    icon: ViewDashboardOutline,
    path: '/home'
  },
  {
    title: 'Locations',
    icon: MapMarkerRadiusOutline,
    path: '/locations'
  },
  {
    title: 'Virtual Customers',
    icon: AccountGroupOutline,
    path: '/virtual-customers',
    action: 'read',
    subject: 'acl-page'
  },
  {
    title: 'Virtual Worker',
    icon: WrenchOutline,
    path: '/virtual-workers',
    action: 'read',
    subject: 'acl-page'
  }
]

export default navigation
