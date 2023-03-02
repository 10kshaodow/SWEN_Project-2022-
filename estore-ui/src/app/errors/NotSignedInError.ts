export class NotSignedInError extends Error {
    constructor(msg: string) {
        super(msg); 
    }
}