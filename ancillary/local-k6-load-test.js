import http from 'k6/http';
import { sleep } from 'k6';
export const options = {
  vus: 100,
  duration: '1s',
};
export default function () {
  http.get('http://localhost:8701/simulate-orders');
  sleep(1);
}