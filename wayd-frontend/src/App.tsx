import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom'
import './App.css'
import ProtectedRoute from './routing/ProtectedRoute'
import LoginPage from './auth/LoginPage'
import HomePage from './home/HomePage.tsx'
import RegisterPage from './auth/RegisterPage.tsx'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage/>}/>
        <Route path="/register" element={<RegisterPage/>}/>
        <Route
          path="/home"
          element={
            <ProtectedRoute>
              <HomePage/>
            </ProtectedRoute>
          }
        />
        <Route path="/" element={<Navigate to="/home"/>}/>
      </Routes>
    </BrowserRouter>
  )
}

export default App
