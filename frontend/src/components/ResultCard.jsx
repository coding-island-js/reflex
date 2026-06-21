export default function ResultCard({ label, value, unit, result }) {
  return (
    <div className="result-card">
      <div className="result-main">
        <span className="result-value">{value}</span>
        <span className="result-unit">{unit}</span>
      </div>
      <div className="result-label">{label}</div>
      {result && (
        <div className="result-stats">
          <div>
            <span className="rs-num">#{result.rank}</span>
            <span className="rs-cap">global rank</span>
          </div>
          <div>
            <span className="rs-num">{result.percentile}%</span>
            <span className="rs-cap">you beat</span>
          </div>
          <div>
            <span className="rs-num">{result.totalPlays}</span>
            <span className="rs-cap">total plays</span>
          </div>
        </div>
      )}
    </div>
  )
}
