import { Controller, useForm } from 'react-hook-form'
import { useNavigate } from 'react-router-dom'
import { useMutation } from '@tanstack/react-query'
import { register } from '../api/auth'
import { Button, TextField } from '@mui/material'

const RegisterPage = () => {
  const navigate = useNavigate()

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<{
    username: string
    password: string
  }>()

  const $register = useMutation({
    mutationFn: register,
    onSuccess: data => {
      localStorage.setItem('Wayd-Token', data)
      navigate('/home')
    },
    onError: error => {
      console.error(error)
    },
  })

  return (
    <form
      onSubmit={handleSubmit(data => {
        $register.mutate({
          username: data.username,
          rawPassword: data.password,
        })
      })}
    >
      <Controller
        name="username"
        control={control}
        defaultValue=""
        render={({ field }) => (
          <TextField
            {...field}
            label="Username"
            error={!!errors.username}
            helperText={errors.username?.message}
            required
          />
        )}
      />
      <Controller
        name="password"
        control={control}
        defaultValue=""
        render={({ field }) => (
          <TextField
            {...field}
            label="Password"
            type="password"
            error={!!errors.password}
            helperText={errors.password?.message}
            required
          />
        )}
      />
      <Button type="submit">Register (at least try)</Button>
    </form>
  )
}

export default RegisterPage
