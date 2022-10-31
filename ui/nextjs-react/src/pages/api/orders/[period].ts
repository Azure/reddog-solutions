import type { NextApiRequest, NextApiResponse } from 'next/types'

export default async function userHandler(req: NextApiRequest, res: NextApiResponse) {

  const SERVER_URL = process.env.SERVER_URL

  const {
    query: { id, name },
    method,
  } = req

  switch (method) {
    case 'GET':
      // Get orders from backend
      const orders = await fetch(`${SERVER_URL}/orders`)
      const ordersJson = await orders.json()
      res.status(200).json(ordersJson)
      break
    case 'PUT':
      res.status(200).json({ id, name: name || `User ${id}` })
      break
    default:
      res.setHeader('Allow', ['GET', 'PUT'])
      res.status(405).end(`Method ${method} Not Allowed`)
  }
}
