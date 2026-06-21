// A stable anonymous identity per browser, so leaderboards work without login.
const ID_KEY = 'reflex_device_id'
const HANDLE_KEY = 'reflex_handle'

export function getDeviceId() {
  let id = localStorage.getItem(ID_KEY)
  if (!id) {
    id = crypto.randomUUID()
    localStorage.setItem(ID_KEY, id)
  }
  return id
}

export function getHandle() {
  return localStorage.getItem(HANDLE_KEY) || ''
}

export function setHandle(handle) {
  localStorage.setItem(HANDLE_KEY, handle)
}
