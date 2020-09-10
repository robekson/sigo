export interface ITipoProcesso {
  id?: number;
  nome?: string;
  descricao?: string;
  processoId?: number;
}

export class TipoProcesso implements ITipoProcesso {
  constructor(public id?: number, public nome?: string, public descricao?: string, public processoId?: number) {}
}
