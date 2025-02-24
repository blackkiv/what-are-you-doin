export const backendUrl = () => {
  const url = import.meta.env.VITE_BACKEND_URL

  return url.includes('PLACEHOLDER') ? 'http://localhost:8765' : url
}
