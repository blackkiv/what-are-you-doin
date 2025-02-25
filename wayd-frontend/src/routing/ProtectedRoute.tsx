import { Navigate } from 'react-router-dom'
import { ReactNode } from 'react'
import FloatingMenu from '../home/FloatingMenu.tsx'

const isAuthenticated = () => {
  return !!localStorage.getItem('Wayd-Token')
}

const ProtectedRoute = ({ children }: { children: ReactNode }) => {
  return isAuthenticated() ? (
    <>
      <FloatingMenu />
      {children}
    </>
  ) : (
    <Navigate to="/login" replace />
  )
}

export default ProtectedRoute
