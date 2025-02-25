import { Box, Divider, Fab, ListItemIcon, Menu, MenuItem } from '@mui/material'
import NavigationIcon from '@mui/icons-material/Navigation'
import React, { useState } from 'react'
import { Dashboard, Logout, Settings } from '@mui/icons-material'
import { useNavigate } from 'react-router-dom'

const FloatingMenu = () => {
  const navigate = useNavigate()

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget)
  }
  const handleClose = () => {
    setAnchorEl(null)
  }

  return (
    <Box sx={{ '& > :not(style)': { m: 1 } }}>
      <Fab
        size="medium"
        color="primary"
        onClick={handleClick}
        sx={theme => ({
          position: 'fixed',
          bottom: theme.spacing(2),
          right: theme.spacing(2),
        })}
      >
        <NavigationIcon />
      </Fab>
      <Menu
        anchorEl={anchorEl}
        id="nav-menu"
        open={!!anchorEl}
        onClose={handleClose}
        onClick={handleClose}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'left',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'left',
        }}
      >
        <MenuItem
          onClick={() => {
            navigate('/dashboard')
            handleClose()
          }}
        >
          <ListItemIcon sx={{ color: '#202020' }}>
            <Dashboard />
          </ListItemIcon>
          Dashboard
        </MenuItem>
        <Divider />
        <MenuItem
          onClick={() => {
            navigate('/preference')
            handleClose()
          }}
        >
          <ListItemIcon>
            <Settings fontSize="small" />
          </ListItemIcon>
          Preference
        </MenuItem>
        <MenuItem
          onClick={() => {
            localStorage.removeItem('Wayd-Token')
            navigate('/login')
            handleClose()
          }}
        >
          <ListItemIcon>
            <Logout fontSize="small" />
          </ListItemIcon>
          Logout
        </MenuItem>
      </Menu>
    </Box>
  )
}

export default FloatingMenu
