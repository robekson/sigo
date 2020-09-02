import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SigoTestModule } from '../../../test.module';
import { FornecedorComponent } from 'app/entities/fornecedor/fornecedor.component';
import { FornecedorService } from 'app/entities/fornecedor/fornecedor.service';
import { Fornecedor } from 'app/shared/model/fornecedor.model';

describe('Component Tests', () => {
  describe('Fornecedor Management Component', () => {
    let comp: FornecedorComponent;
    let fixture: ComponentFixture<FornecedorComponent>;
    let service: FornecedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [FornecedorComponent],
      })
        .overrideTemplate(FornecedorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FornecedorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FornecedorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Fornecedor(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fornecedors && comp.fornecedors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
