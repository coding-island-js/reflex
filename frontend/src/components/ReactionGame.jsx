import { useState, useRef, useEffect, useCallback } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { submitScore, fetchLeaderboard, fetchStats } from '../store/gameSlice'
import ResultCard from './ResultCard'

// idle -> waiting -> ready -> done   (or -> tooearly)
export default function ReactionGame() {
  const dispatch = useDispatch()
  const lastResult = useSelector((s) => s.game.lastResult)
  const [phase, setPhase] = useState('idle')
  const [ms, setMs] = useState(null)
  const timerRef = useRef(null)
  const startRef = useRef(0)

  const clearTimer = () => {
    if (timerRef.current) {
      clearTimeout(timerRef.current)
      timerRef.current = null
    }
  }
  useEffect(() => clearTimer, [])

  const start = () => {
    setMs(null)
    setPhase('waiting')
    const delay = 1500 + Math.random() * 2500
    timerRef.current = setTimeout(() => {
      startRef.current = performance.now()
      setPhase('ready')
    }, delay)
  }

  const handleClick = useCallback(() => {
    if (phase === 'idle' || phase === 'done' || phase === 'tooearly') {
      start()
    } else if (phase === 'waiting') {
      clearTimer()
      setPhase('tooearly')
    } else if (phase === 'ready') {
      const elapsed = Math.round(performance.now() - startRef.current)
      setMs(elapsed)
      setPhase('done')
      dispatch(submitScore({ mode: 'reaction', reactionMs: elapsed })).then(() => {
        dispatch(fetchLeaderboard('reaction'))
        dispatch(fetchStats('reaction'))
      })
    }
  }, [phase, dispatch])

  const stageClass =
    phase === 'ready' ? 'go' : phase === 'tooearly' ? 'bad' : phase === 'waiting' ? 'wait' : ''

  return (
    <div className={`stage ${stageClass}`} onClick={handleClick}>
      {phase === 'idle' && (
        <div className="stage-msg">
          <span className="big">Click to start</span>
          <span className="small">Wait for green, then click as fast as you can.</span>
        </div>
      )}
      {phase === 'waiting' && (
        <div className="stage-msg">
          <span className="big">Wait for green…</span>
        </div>
      )}
      {phase === 'ready' && (
        <div className="stage-msg">
          <span className="big">CLICK!</span>
        </div>
      )}
      {phase === 'tooearly' && (
        <div className="stage-msg">
          <span className="big">Too soon!</span>
          <span className="small">Click to try again.</span>
        </div>
      )}
      {phase === 'done' && (
        <ResultCard label="reaction time" value={ms} unit="ms" result={lastResult} />
      )}
      {phase === 'done' && <span className="retry-hint">Click anywhere to go again</span>}
    </div>
  )
}
