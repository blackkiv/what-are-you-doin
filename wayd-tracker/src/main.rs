use active_win_pos_rs::get_active_window;
use tokio::time::{interval, Duration};

#[tokio::main]
async fn main() {
    let mut interval = interval(Duration::from_secs(5));

    loop {
        interval.tick().await;
        track_active_window().await;
    }
}

/**
this works correctly only on linux and windows.
if i ever want to do extend the support to macos, take a look on it :D
    https://github.com/dimusic/active-window-macos-example
*/
async fn track_active_window() {
    match get_active_window() {
        Ok(window) => { println!("{:#?}", window.app_name) }
        Err(()) => {
            println!("error occurred during window tracking")
        }
    }
}
