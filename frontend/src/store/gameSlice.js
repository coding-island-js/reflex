import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { getDeviceId, getHandle } from '../lib/device'

// Same-origin by default: nginx (prod) and Vite (dev) proxy /api to the backend.
const API = import.meta.env.VITE_API_URL ?? ''

export const submitScore = createAsyncThunk('game/submitScore', async (payload) => {
  const res = await fetch(`${API}/api/scores`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ deviceId: getDeviceId(), handle: getHandle(), ...payload })
  })
  if (!res.ok) throw new Error('Failed to submit score')
  return await res.json()
})

export const fetchLeaderboard = createAsyncThunk('game/fetchLeaderboard', async (mode) => {
  const res = await fetch(`${API}/api/leaderboard?mode=${mode}&limit=10`)
  return await res.json()
})

export const fetchStats = createAsyncThunk('game/fetchStats', async (mode) => {
  const res = await fetch(`${API}/api/stats?mode=${mode}`)
  return await res.json()
})

const gameSlice = createSlice({
  name: 'game',
  initialState: {
    mode: 'reaction',
    lastResult: null,
    leaderboard: [],
    stats: null,
    submitting: false,
    error: null
  },
  reducers: {
    setMode(state, action) {
      state.mode = action.payload
      state.lastResult = null
    },
    clearResult(state) {
      state.lastResult = null
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(submitScore.pending, (state) => {
        state.submitting = true
        state.error = null
      })
      .addCase(submitScore.fulfilled, (state, action) => {
        state.submitting = false
        state.lastResult = action.payload
      })
      .addCase(submitScore.rejected, (state, action) => {
        state.submitting = false
        state.error = action.error.message
      })
      .addCase(fetchLeaderboard.fulfilled, (state, action) => {
        state.leaderboard = action.payload
      })
      .addCase(fetchStats.fulfilled, (state, action) => {
        state.stats = action.payload
      })
  }
})

export const { setMode, clearResult } = gameSlice.actions
export default gameSlice.reducer
