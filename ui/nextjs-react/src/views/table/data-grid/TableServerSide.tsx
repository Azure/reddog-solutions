// ** React Imports
import {useCallback, useEffect, useState} from 'react'

// ** MUI Imports
import Box from '@mui/material/Box'
import Card from '@mui/material/Card'
import Typography from '@mui/material/Typography'
import CardHeader from '@mui/material/CardHeader'
import {DataGrid, GridColumns, GridRenderCellParams, GridSortModel} from '@mui/x-data-grid'

// ** ThirdParty Components
import axios from 'axios'

// ** Custom Components
import CustomChip from 'src/@core/components/mui/chip'
import ServerSideToolbar from 'src/views/table/data-grid/ServerSideToolbar'

// ** Types Imports
import {ThemeColor} from 'src/@core/layouts/types'
import {OrdersGridRowType} from "src/@core/utils/types";

// ** Utils Import

interface StatusObj {
  [key: number]: {
    title: string
    color: ThemeColor
  }
}

type SortType = 'asc' | 'desc' | undefined | null


const statusObj: StatusObj = {
  1: { title: 'in-flight', color: 'primary' },
  2: { title: 'Completed', color: 'success' }
}

const columns: GridColumns = [
  {
    flex: 0.25,
    minWidth: 180,
    field: 'firstName',
    headerName: 'Name',
    renderCell: (params: GridRenderCellParams) => {
      const { row } = params

      return (
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <Box sx={{ display: 'flex', flexDirection: 'column' }}>
            <Typography noWrap variant='body2' sx={{ color: 'text.primary', fontWeight: 600 }}>
              {row.firstName}
            </Typography>
            <Typography noWrap variant='caption'>
              {row.lastName}
            </Typography>
          </Box>
        </Box>
      )
    }
  },
  {
    flex: 0.175,
    minWidth: 120,
    headerName: 'Date',
    field: 'orderCompletedDate',
    renderCell: (params: GridRenderCellParams) => (
      <Typography variant='body2' sx={{ color: 'text.primary' }}>
        {params.row.orderCompletedDate}
      </Typography>
    )
  },
  {
    flex: 0.175,
    minWidth: 110,
    field: 'loyaltyId',
    headerName: 'Loyalty ID',
    renderCell: (params: GridRenderCellParams) => (
      <Typography variant='body2' sx={{ color: 'text.primary' }}>
        {params.row.loyaltyId}
      </Typography>
    )
  },
  {
    flex: 0.125,
    field: 'orderTotal',
    minWidth: 150,
    headerName: 'Order total',
    renderCell: (params: GridRenderCellParams) => (
      <Typography variant='body2' sx={{ color: 'text.primary' }}>
        ${params.row.orderTotal}
      </Typography>
    )
  }
]

const TableServerSide = () => {
  // ** State
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState<number>(0)
  const [sort, setSort] = useState<SortType>('asc')
  const [pageSize, setPageSize] = useState<number>(7)
  const [rows, setRows] = useState<OrdersGridRowType[]>([])
  const [sortColumn, setSortColumn] = useState<string>('full_name')

  function loadServerRows(currentPage: number, data: OrdersGridRowType[]) {
    return data.slice(currentPage * pageSize, (currentPage + 1) * pageSize)
  }

  const fetchTableData = useCallback(
    async (sort: SortType, column: string) => {
      await axios
        .get('/api/orders', {
          params: {
            sort,
            column
          }
        })
        .then(res => {
          setTotal(res.data.orderTotal)
          setRows(loadServerRows(page, res.data))
        })
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [page, pageSize]
  )

  useEffect(() => {
    fetchTableData(sort, sortColumn)
  }, [fetchTableData, sort, sortColumn])

  const handleSortModel = (newModel: GridSortModel) => {
    if (newModel.length) {
      setSort(newModel[0].sort)
      setSortColumn(newModel[0].field)
      fetchTableData(newModel[0].sort,newModel[0].field)
    } else {
      setSort('asc')
      setSortColumn('full_name')
    }
  }


  return (
    <Card>
      <CardHeader title='In-flight orders' />
      <DataGrid
        autoHeight
        pagination
        rows={rows}
        rowCount={total}
        columns={columns}
        checkboxSelection
        pageSize={pageSize}
        sortingMode='server'
        paginationMode='server'
        onSortModelChange={handleSortModel}
        getRowId={row => row.orderId}
        rowsPerPageOptions={[7, 10, 25, 50]}
        onPageChange={newPage => setPage(newPage)}
        components={{ Toolbar: ServerSideToolbar }}
        onPageSizeChange={newPageSize => setPageSize(newPageSize)}
      />
    </Card>
  )
}

export default TableServerSide
