import { useState } from 'react'
import { getHandle, setHandle } from '../lib/device'

export default function HandleInput() {
  const [value, setValue] = useState(getHandle())
  const [saved, setSaved] = useState(false)

  const save = () => {
    setHandle(value.trim().slice(0, 20))
    setSaved(true)
    setTimeout(() => setSaved(false), 1500)
  }

  return (
    <div className="handle">
      <label htmlFor="handle">Leaderboard name</label>
      <div className="handle-row">
        <input
          id="handle"
          value={value}
          maxLength={20}
          placeholder="anon"
          onChange={(e) => setValue(e.target.value)}
        />
        <button onClick={save}>{saved ? 'Saved ✓' : 'Save'}</button>
      </div>
    </div>
  )
}
