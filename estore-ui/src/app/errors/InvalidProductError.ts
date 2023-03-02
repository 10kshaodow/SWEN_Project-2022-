export class InvalidProductError extends Error {
    constructor(msg: string) {
        super(msg); 
    }
}