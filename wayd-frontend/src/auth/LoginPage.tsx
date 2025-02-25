import { Button, Stack, TextField } from '@mui/material'
import { useMutation } from '@tanstack/react-query'
import { Controller, useForm } from 'react-hook-form'
import { login } from '../api/auth'
import { useNavigate } from 'react-router-dom'

const LoginPage = () => {
  const navigate = useNavigate()

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<{
    username: string
    password: string
  }>()

  const $login = useMutation({
    mutationFn: login,
    onSuccess: data => {
      localStorage.setItem('Wayd-Token', data)
      navigate('/dashboard')
    },
    onError: error => {
      console.error(error)
    },
  })

  return (
    <form
      onSubmit={handleSubmit(data => {
        $login.mutate({ username: data.username, rawPassword: data.password })
      })}
    >
      <Stack spacing={1}>
        <Controller
          name="username"
          control={control}
          defaultValue=""
          render={({ field }) => (
            <TextField
              {...field}
              variant="standard"
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
              variant="standard"
              label="Password"
              type="password"
              error={!!errors.password}
              helperText={errors.password?.message}
              required
            />
          )}
        />
        <Button type="submit">Login (at least try)</Button>
        <Button onClick={() => navigate('/register')}>register me :c</Button>
      </Stack>
    </form>
  )
}

export default LoginPage
