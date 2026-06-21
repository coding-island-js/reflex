import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    // In dev, forward API + guide requests to the Spring Boot backend.
    proxy: {
      '/api': 'http://localhost:8080',
      '/learn': 'http://localhost:8080'
    }
  }
})
