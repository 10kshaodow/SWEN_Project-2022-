import { HealthStatus } from "./HealthStatus";
import { ImageSource } from "./ImageSource";

export interface HealthReport {
    reportID: number;
    falconID: number;
    reportDate: Date;
    healthStatus: HealthStatus;
    healthDescription: string;
    keyNotes: string[];
    diet: DietStatus;
    fitness: FitnessStatus;
    social: SocialStatus;
    reproductiveStatus: boolean;
    fileName: string[];
    fileSource: ImageSource[];
}

export enum DietStatus {
    Nutritional = "Nutritional", 
    Balanced = "Balanced",
    Junk = "Junk"
}

export enum FitnessStatus {
    Active = "Active",
    Average = "Average",
    Dormant = "Dormant",
}

export enum SocialStatus {
    Lively = "Lively",
    Rowdy = "Rowdy",
    Reserved = "Reserved"
}