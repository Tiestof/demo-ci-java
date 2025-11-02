import http from 'k6/http';
import { check, sleep } from 'k6';

/**
 * Prueba de performance simple sobre un endpoint público.
 * Simula 5 usuarios concurrentes durante 10 segundos.
 * Evalúa latencia (p95) y tasa de errores (<1%).
 */
export const options = {
  vus: 5,           // 5 usuarios virtuales simultáneos
  duration: '10s',  // durante 10 segundos
  thresholds: {
    http_req_failed: ['rate<0.01'],   // menos de 1% de fallos
    http_req_duration: ['p(95)<500'], // 95% de solicitudes < 500 ms
  },
};

export default function () {
  const url = 'https://test.k6.io/'; // endpoint público de k6

  // Petición GET simple
  const res = http.get(url);

  // Validaciones básicas
  check(res, {
    'status is 200': (r) => r.status === 200,
    'response time < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}
