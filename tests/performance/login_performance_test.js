import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 5,            // 5 usuarios concurrentes
  duration: '10s',   // por 10 segundos
  thresholds: {
    http_req_failed: ['rate<0.01'],    // menos de 1% de fallos
    http_req_duration: ['p(95)<500'],  // 95% de peticiones < 500 ms
  },
};

export default function () {
  const url = 'https://test-api.k6.io/auth/basic/login/'; // endpoint demo público
  const payload = JSON.stringify({
    username: 'admin',
    password: '1234',
  });

  const params = {
    headers: { 'Content-Type': 'application/json' },
  };

  const res = http.post(url, payload, params);

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response time < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}
