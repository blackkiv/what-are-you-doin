import { Navigate, useNavigate } from 'react-router-dom'
import { createContext, ReactNode, Suspense, useEffect } from 'react'
import FloatingMenu from '../home/FloatingMenu.tsx'
import { useQuery } from '@tanstack/react-query'
import { userData, UserDataResponse } from '../api/user.ts'
import { AxiosError } from 'axios'

export const UserContext = createContext({
  user: {} as UserDataResponse,
  refreshUser: () => {},
})

const isAuthenticated = () => {
  return !!localStorage.getItem('Wayd-Token')
}

const ProtectedRoute = ({ children }: { children: ReactNode }) => {
  const navigate = useNavigate()
  const $userData = useQuery({ queryKey: ['userData'], queryFn: userData })

  useEffect(() => {
    if ($userData.error instanceof AxiosError) {
      const error = $userData.error
      if (error.status === 400) {
        localStorage.removeItem('Wayd-Token')
        navigate('/login')
      }
    }
  }, [$userData.error])

  const user = $userData.data

  return isAuthenticated() ? (
    !$userData.isLoading && user && (
      <>
        <Suspense>
          <UserContext.Provider
            value={{
              user: user,
              refreshUser: async () => await $userData.refetch(),
            }}
          >
            <FloatingMenu />
            {children}
          </UserContext.Provider>
        </Suspense>
      </>
    )
  ) : (
    <Navigate to="/login" replace />
  )
}

export default ProtectedRoute
