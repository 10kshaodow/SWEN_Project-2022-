export class ProductNameTakenError extends Error {
    constructor(msg: string) {
        super(msg); 
    }
}