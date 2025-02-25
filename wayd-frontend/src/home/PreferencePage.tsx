import { useMutation } from '@tanstack/react-query'
import { updateUserPreference } from '../api/user'
import {
  Button,
  Checkbox,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Paper,
  Stack,
  Typography,
} from '@mui/material'
import { useContext, useState } from 'react'
import {
  FormProvider,
  useFieldArray,
  useForm,
  useFormContext,
} from 'react-hook-form'
import { UserContext } from '../protected-routes/ProtectedRoute.tsx'

const not = (a: AppName[], b: AppName[]) =>
  a.filter(value => !b.includes(value))

const intersection = (a: AppName[], b: AppName[]) =>
  a.filter(value => b.includes(value))

type AppName = {
  name: string
}

type FormValues = {
  apps: AppName[]
  appsBlacklist: AppName[]
  appsWhitelist: AppName[]
}

const AppPreference = () => {
  const { control } = useFormContext<FormValues>()
  const [checked, setChecked] = useState<AppName[]>([])

  const blacklist = useFieldArray({
    name: 'appsBlacklist',
    control: control,
  })
  const apps = useFieldArray({
    name: 'apps',
    control: control,
  })
  const whitelist = useFieldArray({
    name: 'appsWhitelist',
    control: control,
  })

  const leftChecked = intersection(checked, blacklist.fields)
  const centralChecked = intersection(checked, apps.fields)
  const rightChecked = intersection(checked, whitelist.fields)

  const handleToggle = (value: AppName) => () => {
    const currentIndex = checked.indexOf(value)
    const newChecked = [...checked]

    if (currentIndex === -1) {
      newChecked.push(value)
    } else {
      newChecked.splice(currentIndex, 1)
    }

    setChecked(newChecked)
  }

  const handleWhitelist = (direction: 'left' | 'right' | 'all') => {
    if (direction === 'right') {
      whitelist.append(centralChecked)
      apps.replace(not(apps.fields, centralChecked))
      setChecked(not(checked, centralChecked))
    } else if (direction === 'left') {
      apps.append(rightChecked)
      whitelist.replace(not(whitelist.fields, rightChecked))
      setChecked(not(checked, rightChecked))
    } else {
      apps.append(whitelist.fields)
      whitelist.replace([])
      setChecked(not(checked, apps.fields))
    }
  }

  const handleBlacklist = (direction: 'left' | 'right' | 'all') => {
    if (direction === 'left') {
      blacklist.append(centralChecked)
      apps.replace(not(apps.fields, centralChecked))
      setChecked(not(checked, centralChecked))
    } else if (direction === 'right') {
      apps.append(leftChecked)
      blacklist.replace(not(blacklist.fields, leftChecked))
      setChecked(not(checked, leftChecked))
    } else {
      apps.append(blacklist.fields)
      blacklist.replace([])
      setChecked(not(checked, apps.fields))
    }
  }

  const list = (items: AppName[]) => (
    <Paper sx={{ width: '15vh', height: '40vh', overflow: 'auto' }}>
      <List dense component="div" role="list">
        {items.map(value => {
          const labelId = `transfer-list-item-${value}-label`
          return (
            <ListItemButton key={value.name} onClick={handleToggle(value)}>
              <ListItemIcon>
                <Checkbox
                  checked={checked.includes(value)}
                  tabIndex={-1}
                  disableRipple
                  inputProps={{
                    'aria-labelledby': labelId,
                  }}
                />
              </ListItemIcon>
              <ListItemText id={labelId} primary={value.name} />
            </ListItemButton>
          )
        })}
      </List>
    </Paper>
  )

  return (
    <Stack direction="row" spacing={1}>
      <Stack spacing={1}>
        <Typography>Blacklist</Typography>
        {list(blacklist.fields)}
      </Stack>
      <Stack
        spacing={1}
        sx={{
          justifyContent: 'center',
        }}
      >
        <Button
          sx={{ my: 0.5 }}
          variant="outlined"
          size="small"
          onClick={() => handleBlacklist('left')}
          disabled={apps.fields.length === 0}
        >
          &lt;
        </Button>
        <Button
          sx={{ my: 0.5 }}
          variant="outlined"
          size="small"
          onClick={() => handleBlacklist('right')}
          disabled={blacklist.fields.length === 0}
        >
          &gt;
        </Button>
        <Button
          sx={{ my: 0.5 }}
          variant="outlined"
          size="small"
          onClick={() => handleBlacklist('all')}
          disabled={blacklist.fields.length === 0}
        >
          ≫
        </Button>
      </Stack>
      <Stack spacing={1}>
        <Typography>Apps</Typography>
        {list(apps.fields)}
      </Stack>
      <Stack
        spacing={1}
        sx={{
          justifyContent: 'center',
        }}
      >
        <Button
          sx={{ my: 0.5 }}
          variant="outlined"
          size="small"
          onClick={() => handleWhitelist('right')}
          disabled={apps.fields.length === 0}
        >
          &gt;
        </Button>
        <Button
          sx={{ my: 0.5 }}
          variant="outlined"
          size="small"
          onClick={() => handleWhitelist('left')}
          disabled={whitelist.fields.length === 0}
        >
          &lt;
        </Button>
        <Button
          sx={{ my: 0.5 }}
          variant="outlined"
          size="small"
          onClick={() => handleWhitelist('all')}
          disabled={whitelist.fields.length === 0}
        >
          ≪
        </Button>
      </Stack>
      <Stack spacing={1}>
        <Typography>Whitelist</Typography>
        {list(whitelist.fields)}
      </Stack>
    </Stack>
  )
}

const PreferenceForm = ({
  defaultData,
  onRefresh,
}: {
  defaultData: FormValues
  onRefresh: () => void
}) => {
  const form = useForm<FormValues>({
    defaultValues: defaultData,
  })
  const $updateUserPreference = useMutation({
    mutationFn: updateUserPreference,
    onSuccess: onRefresh,
  })

  return (
    <form
      onSubmit={form.handleSubmit(data => {
        $updateUserPreference.mutate({
          appsWhitelist: data.appsWhitelist.map(app => app.name),
          appsBlacklist: data.appsBlacklist.map(app => app.name),
        })
      })}
    >
      <FormProvider {...form}>
        <Stack spacing={2} alignItems="center">
          <AppPreference />
          <Button variant="contained" type="submit">
            Save
          </Button>
        </Stack>
      </FormProvider>
    </form>
  )
}

const PreferencePage = () => {
  const { user, refreshUser } = useContext(UserContext)

  return (
    <Stack spacing={10}>
      <Typography variant="h2">Welcome, {user.username}</Typography>
      <PreferenceForm
        onRefresh={refreshUser}
        defaultData={{
          apps: user?.preference.apps.map(app => ({ name: app })) ?? [],
          appsWhitelist:
            user?.preference.appsWhitelist.map(app => ({ name: app })) ?? [],
          appsBlacklist:
            user?.preference.appsBlacklist.map(app => ({ name: app })) ?? [],
        }}
      />
    </Stack>
  )
}

export default PreferencePage
