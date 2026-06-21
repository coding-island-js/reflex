import { useState, useRef, useCallback } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { submitScore, fetchLeaderboard, fetchStats } from '../store/gameSlice'
import ResultCard from './ResultCard'

const TARGETS = 20

// idle -> playing -> done
export default function AimGame() {
  const dispatch = useDispatch()
  const lastResult = useSelector((s) => s.game.lastResult)
  const [phase, setPhase] = useState('idle')
  const [hits, setHits] = useState(0)
  const [misses, setMisses] = useState(0)
  const [pos, setPos] = useState({ x: 50, y: 50 })
  const [accuracy, setAccuracy] = useState(null)
  const stageRef = useRef(null)

  const spawn = () => {
    setPos({ x: 8 + Math.random() * 84, y: 12 + Math.random() * 76 })
  }

  const start = () => {
    setHits(0)
    setMisses(0)
    setAccuracy(null)
    setPhase('playing')
    spawn()
  }

  const finish = useCallback(
    (finalHits, finalMisses) => {
      const total = finalHits + finalMisses
      const acc = total === 0 ? 100 : Math.round((finalHits / total) * 1000) / 10
      setAccuracy(acc)
      setPhase('done')
      dispatch(submitScore({ mode: 'aim', targetsHit: finalHits, accuracy: acc })).then(() => {
        dispatch(fetchLeaderboard('aim'))
        dispatch(fetchStats('aim'))
      })
    },
    [dispatch]
  )

  const hitTarget = (e) => {
    e.stopPropagation()
    const next = hits + 1
    setHits(next)
    if (next >= TARGETS) {
      finish(next, misses)
    } else {
      spawn()
    }
  }

  const missClick = () => {
    if (phase === 'playing') setMisses((m) => m + 1)
  }

  return (
    <div className="stage aim" ref={stageRef} onClick={missClick}>
      {phase === 'idle' && (
        <div className="stage-msg" onClick={start}>
          <span className="big">Click to start</span>
          <span className="small">Pop {TARGETS} targets as fast as you can. Misses cost accuracy.</span>
        </div>
      )}

      {phase === 'playing' && (
        <>
          <div className="aim-hud">
            <span>{hits}/{TARGETS}</span>
            <span className="aim-miss">{misses} misses</span>
          </div>
          <button
            className="target"
            style={{ left: `${pos.x}%`, top: `${pos.y}%` }}
            onClick={hitTarget}
            aria-label="target"
          />
        </>
      )}

      {phase === 'done' && (
        <div className="stage-msg" onClick={start}>
          <ResultCard label={`accuracy · ${accuracy}%`} value={hits * accuracy} unit="pts" result={lastResult} />
          <span className="retry-hint">Click to play again</span>
        </div>
      )}
    </div>
  )
}
