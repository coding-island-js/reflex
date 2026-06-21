import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchLeaderboard, fetchStats } from '../store/gameSlice'

export default function Leaderboard() {
  const dispatch = useDispatch()
  const mode = useSelector((s) => s.game.mode)
  const rows = useSelector((s) => s.game.leaderboard)
  const stats = useSelector((s) => s.game.stats)

  useEffect(() => {
    dispatch(fetchLeaderboard(mode))
    dispatch(fetchStats(mode))
  }, [mode, dispatch])

  return (
    <section className="leaderboard">
      <div className="lb-head">
        <h2>Leaderboard</h2>
        {stats && stats.totalPlays > 0 && (
          <span className="lb-stat">
            {stats.totalPlays.toLocaleString()} plays · best {stats.bestScore}
          </span>
        )}
      </div>

      {rows.length === 0 ? (
        <p className="lb-empty">No scores yet — be the first.</p>
      ) : (
        <table className="lb-table">
          <thead>
            <tr>
              <th>#</th>
              <th>Player</th>
              <th>Score</th>
            </tr>
          </thead>
          <tbody>
            {rows.map((r) => (
              <tr key={`${r.playerId}-${r.rank}`}>
                <td className="lb-rank">{r.rank}</td>
                <td>{r.handle || 'anon'}</td>
                <td className="lb-score">{r.bestScore}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  )
}
