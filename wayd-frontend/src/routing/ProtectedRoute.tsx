import {Navigate} from 'react-router-dom'
import {ReactNode} from 'react'

const isAuthenticated = () => {
  return !!localStorage.getItem('Wayd-Token')
}

const ProtectedRoute = ({children}: { children: ReactNode }) => {
  return isAuthenticated() ? children : <Navigate to="/login" replace/>
}

export default ProtectedRoute
