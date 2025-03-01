import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import './App.css'
import { DashboardPage, LoginPage, PreferencePage, RegisterPage } from './ui'
import { ProtectedRoute } from './util'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <DashboardPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="/preference"
          element={
            <ProtectedRoute>
              <PreferencePage />
            </ProtectedRoute>
          }
        />
        <Route path="/" element={<Navigate to="/dashboard" />} />
        <Route path="*" element={<Navigate to="/" replace={true} />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
