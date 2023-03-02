export class UsernameExistsError extends Error {
    constructor(msg: string) {
        super(msg); 
    }
}