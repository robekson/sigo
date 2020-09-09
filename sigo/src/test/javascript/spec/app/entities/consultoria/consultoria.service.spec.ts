import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ConsultoriaService } from 'app/entities/consultoria/consultoria.service';
import { IConsultoria, Consultoria } from 'app/shared/model/consultoria.model';

describe('Service Tests', () => {
  describe('Consultoria Service', () => {
    let injector: TestBed;
    let service: ConsultoriaService;
    let httpMock: HttpTestingController;
    let elemDefault: IConsultoria;
    let expectedResult: IConsultoria | IConsultoria[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConsultoriaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Consultoria(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataContratacao: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Consultoria', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataContratacao: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataContratacao: currentDate,
          },
          returnedFromService
        );

        service.create(new Consultoria()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Consultoria', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            cnpj: 'BBBBBB',
            dataContratacao: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB',
            telefone: 'BBBBBB',
            tipoServicoPrestado: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataContratacao: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Consultoria', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            cnpj: 'BBBBBB',
            dataContratacao: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB',
            telefone: 'BBBBBB',
            tipoServicoPrestado: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataContratacao: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Consultoria', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
