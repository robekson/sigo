import { Moment } from 'moment';

export interface IConsultoria {
  id?: number;
  nome?: string;
  cnpj?: string;
  dataContratacao?: Moment;
  email?: string;
  telefone?: string;
  tipoServicoPrestado?: string;
}

export class Consultoria implements IConsultoria {
  constructor(
    public id?: number,
    public nome?: string,
    public cnpj?: string,
    public dataContratacao?: Moment,
    public email?: string,
    public telefone?: string,
    public tipoServicoPrestado?: string
  ) {}
}
