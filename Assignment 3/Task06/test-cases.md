# Task 6 – Test Cases and Analysis

| ID | Test | Expected result |
|---|---|---|
| TC01 | Run TokenCounter with 20 produced tokens and two consumers | Final sum is 210 |
| TC02 | Observe all counter-size messages | Size stays between 0 and 5 |
| TC03 | Producer attempts to add when full | Producer waits until space is available |
| TC04 | Consumer attempts to remove when empty | Consumer waits until a token is available |
| TC05 | Run opposite-order wallet transfers | Demonstrates deadlock/circular wait |
| TC06 | Run transfers with ascending account-ID lock order | Both complete; total balance is preserved |
| TC07 | Run ArrayBlockingQueue alternative | Capacity 5; final sum is 210 |
| TC08 | Compare while and if around wait() | while rechecks condition; if does not |

## Four conditions for deadlock
1. Mutual exclusion: each wallet monitor is held by one thread at a time.
2. Hold and wait: a thread holds one wallet lock while requesting another.
3. No preemption: a monitor cannot be forcibly taken from its owner.
4. Circular wait: one thread holds A and waits for B while another holds B and waits for A.

## Prevention
Acquire both wallet locks in ascending account-ID order. Since every transfer follows the same strict order, circular wait cannot form.

## Producer–consumer correctness
Both put() and take() are synchronized. put() waits while the buffer is full; take() waits while it is empty. wait() releases the object's monitor while waiting and reacquires it before returning. After changing the circular buffer, each method calls notifyAll(). The count remains in [0, 5].

## Why while instead of if
A thread may wake spuriously, or another thread may change the buffer before the awakened thread reacquires the monitor. while rechecks the condition; if checks only once.

## sleep() versus wait()
sleep() pauses the current thread and does not release monitors. wait() must be called while owning the object's monitor; it releases that monitor while waiting and reacquires it before returning.

## notify() versus notifyAll()
notify() wakes one arbitrary waiter. With multiple producer/consumer wait conditions, it can wake a thread that still cannot proceed. notifyAll() wakes all waiters, which then recheck their conditions. It is the safer general choice here.

## Alternatives and complexity
- ArrayBlockingQueue<Integer>(5): put/take block automatically; typical operation O(1), storage O(C) for capacity C. Less error-prone than manual wait/notify.
- ReentrantLock.tryLock(timeout): acquire both locks with a timeout; if either fails, release any acquired lock in finally and retry or report failure. Transfer work is O(1) excluding lock wait; lock storage O(A) for A accounts.
- Wallet transfer: O(1) account operations excluding contention; account storage O(A).
- Token buffer: O(1) per put/take; storage O(5), or O(C) for capacity C.

## Joint conclusion
Both problems coordinate access to shared state. A global lock order prevents circular wait in transfers. A bounded producer-consumer buffer uses synchronized state checks, wait loops and notifications to allow progress without exceeding its capacity.
