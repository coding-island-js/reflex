import { useDispatch, useSelector } from 'react-redux'
import { setMode } from './store/gameSlice'
import ReactionGame from './components/ReactionGame'
import AimGame from './components/AimGame'
import Leaderboard from './components/Leaderboard'
import HandleInput from './components/HandleInput'

export default function App() {
  const dispatch = useDispatch()
  const mode = useSelector((s) => s.game.mode)

  return (
    <div className="app">
      <header className="header">
        <h1 className="logo">REFLEX</h1>
        <p className="tagline">How fast are your reflexes? Play free — no signup.</p>
      </header>

      <div className="tabs">
        <button
          className={`tab ${mode === 'reaction' ? 'active' : ''}`}
          onClick={() => dispatch(setMode('reaction'))}
        >
          Reaction Time
        </button>
        <button
          className={`tab ${mode === 'aim' ? 'active' : ''}`}
          onClick={() => dispatch(setMode('aim'))}
        >
          Aim Trainer
        </button>
      </div>

      <main className="stage-wrap">
        {mode === 'reaction' ? <ReactionGame /> : <AimGame />}
      </main>

      <HandleInput />
      <Leaderboard />

      <footer className="footer">
        <a className="footer-link" href="/learn">Reaction time guides</a>
        <span>Built with Java · Spring Boot · PostgreSQL · React + Redux · Docker</span>
      </footer>
    </div>
  )
}
